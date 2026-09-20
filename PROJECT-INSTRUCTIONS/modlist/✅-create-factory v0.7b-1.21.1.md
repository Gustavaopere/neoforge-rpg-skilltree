# Create: Factory

## Propriedades do registro

- **Mod:** Create: Factory
- **Arquivo JAR:** create_factory-0.7b-1.21.1.jar
- **Versão 1.21.1:** 0.7b-1.21.1
- **Categoria:** Comida, Automação, Tecnologia
- **Função:** Addon culinário-industrial do Create com foods, quatro fluidos próprios, jars de armazenamento e fluxo de recipe Jar Dipping integrado a JEI/Create.
- **Dependências:** Create 6.0.10 obrigatório. Create Confectionery 1.1.3. está fisicamente presente como integração opcional; Ecologics não está presente como JAR top-level atual.
- **Estado no pack:** Integrado ao Github
- **Estado da pesquisa:** Verificado
- **Compatibilidade/Riscos:** Riscos centrais: jar perder/duplicar fluido ou componentes de potion; divergência item↔block capability; Jar Dipping executar em duplicidade; recipe overlap com Create Confectionery; tags/reload deixarem state stale.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/create-factory
- **Procedência:** modlist.txt física atual de 16/09/2026 + runtime `create_factory` 0.7b-1.21.1 + CurseForge oficial revalidado em 20/09/2026; 0.7b continua a release mais recente para 1.21.1, com source da linha 1.21.1 usado apenas como evidência não-byte-exata.
- **Observações:** JAR físico `create_factory-0.7b-1.21.1.jar`, mod id `create_factory`, runtime metadata `0.7b-1.21.1`; a publicação oficial rotula a release como 0.7b para NeoForge 1.21.1. Changelog 0.7b corrige potions inside jars.
- **Atualização/Status:** REVALIDADO EM 20/09/2026 — lote físico #141: create_factory-0.7b-1.21.1.jar / 0.7b-1.21.1 confirmados como latest NeoForge 1.21.1; foods/fluidos/jars, Jar Dipping, Confectionery integration e potion-in-jar regression permanecem atuais.
- **Decisão:** Sem decisão
- **Sobreposição:** Compartilha espaço culinário com Create Confectionery e outros addons; integração Confectionery é concreta. Comparar recipes/inputs/outputs para detectar rotas redundantes ou loops; não classificar o mod inteiro como duplicata.

# Dossiê operacional — padrão Alex's Mobs
> 🧇 **Identidade física confirmada:** `create_factory-0.7b-1.21.1.jar`, mod id `create_factory`, runtime metadata `0.7b-1.21.1`. A publicação oficial identifica a release como **0.7b** para NeoForge 1.21.1, Client & Server.

## 1. Papel e authority
Create: Factory é um addon culinário/industrial do Create. **Create 6.0.10** permanece owner dos process types, fluid transfer, belts, mixers e automação base. Factory owns seus foods, fluidos, jars, recipes e a integração de Jar Dipping/JEI. Vanilla e mods opcionais continuam owners dos ingredientes que fornecem.
## 2. Conteúdo registrado confirmado no source 1.21.1
O branch oficial `1.21.1-dev` registra alimentos como Waffle, Uncooked Waffle, Cake Paste, Honey Roll, Chocolate Roll, versões com jams, glazed berries, apples e waffles. Alguns foods aplicam efeitos vanilla, incluindo Regeneration, Glowing e Movement Speed conforme os itens específicos do source.
Essa lista é derivada do source atual da linha 1.21.1; não presumir que marketing/resumos externos sejam mais autoritativos que o registry efetivo.
## 3. Fluidos próprios
O source registra quatro fluidos próprios:
- `sweet_berries_jam`;
- `glow_berries_jam`;
- `spread` — Chocolate Walnut Spread;
- `nectar`.
Eles possuem buckets e propriedades próprias de densidade/viscosidade/flow. O addon, não Create, é owner desses fluidos; Create apenas fornece infraestrutura de processamento/manuseio.
## 4. Jars
A árvore do source confirma `JarBlock`, `JarBlockEntity`, `JarItem` e `JarItemFluidHandler`, além de variantes coloridas de jars. Isso torna armazenamento/serialização de fluido uma superfície central do mod, não mera decoração.
Transferência entre jar, item, tank, pipe e recipe precisa conservar exatamente quantidade e tipo de fluido, inclusive durante break/place e reload.
## 5. Jar Dipping e JEI
O source inclui `JarDippingCategory` e `JarDippingJeiPlugin`. Portanto existe um fluxo de recipe próprio apresentado no JEI para **Jar Dipping**. JEI é apenas visualização; recipe matching, consumo e resultado devem permanecer server-authoritative.
## 6. Release 0.7b — regressão de potion em jars
O changelog exato da 0.7b registra **fix para potions inside jars**. Esse ponto vira regression gate obrigatório: armazenar/transferir potion state não pode perder tipo, componentes ou quantidade, nem duplicar conteúdo no round-trip item↔block.
## 7. Recipes e automação Create
Os recursos do branch incluem `data/create_factory/recipe` e tags de fluid/item. O addon usa a infraestrutura Create para automatizar sua cadeia culinária. `/reload` pode alterar recipe/tag state; máquinas e viewers não devem manter recipe removida ou output stale.
## 8. Create Confectionery
A publicação oficial informa compatibilidade opcional com **Create: Confectionery** para receitas extras. Esse mod está fisicamente instalado no pack (`create-confectionery1.21.1_v1.1.3b.jar`). O source Factory possui registro dedicado `ConfectioneryItems`, confirmando uma superfície concreta de integração.
Comparar receitas por input/output e ID real para evitar chocolate/caramel routes duplicadas ou custo incoerente.
## 9. Ecologics
O upstream também declara Ecologics como integração opcional para receitas extras e possui `EcologicsItems` no source. **Ecologics não foi encontrado como JAR top-level na modlist física atual**, portanto essa integração não é runtime ativa segundo a autoridade física do pack.
## 10. Foods e efeitos
Os alimentos com efeitos devem aplicar os `MobEffectInstance` definidos pelo provider apenas quando consumidos. Scripts de hunger/RPG não devem reaplicar efeitos por nome do item. Balanceamento deve considerar nutrition, saturation e efeitos concretos do runtime.
## 11. Client/server e multiplayer
Models, JEI e animações são client-facing. Fluid contents, food consumption, recipe execution, jar inventory/capability e efeitos são server-authoritative. Dois players manipulando o mesmo jar não podem extrair a mesma quantidade duas vezes.
## 12. Lifecycle
Testar place→fill→break→place de jars, chunk unload, restart, recipe reload, automação com pipes/Spouts e movimentação do item de jar entre inventários. Fluid state e data components precisam sobreviver sem perda ou duplicação.
## 13. Riscos
1. Jar duplica/perde fluido em break/place.
2. Potion em jar perde componentes — regressão corrigida na 0.7b.
3. Jar Dipping consome input duas vezes ou produz output duplicado.
4. Fluid capability diverge entre block e item.
5. Create Confectionery adiciona recipe equivalente com custo menor/loop.
6. Tags capturam fluido/item de outro provider indevidamente.
7. `/reload` deixa JEI ou machine state stale.
8. Efeitos de food são duplicados por outra integração.
9. Branch `1.21.1-dev` diverge do binário publicado; internals não pinados devem ser tratados com cautela.
## 14. Matriz de testes
- [ ] Dedicated server inicia com Factory 0.7b + Create 6.0.10.
- [ ] Jars armazenam e recuperam os quatro fluidos próprios sem dupe/loss.
- [ ] Potion em jar preserva conteúdo/componentes após save/reload e break/place.
- [ ] Jar Dipping aparece no JEI e executa exatamente uma vez no servidor.
- [ ] Waffles/rolls/apples aplicam nutrition/effects coerentes com o provider.
- [ ] Integração com Create Confectionery não gera recipe loop ou duplicidade indevida.
- [ ] `/reload` atualiza recipes/tags sem cache stale.
- [ ] Dois clientes manipulando o mesmo jar mantêm state único.
- [ ] Restart durante automação de fluido não duplica conteúdo.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 15. Evidências e limites
A modlist física confirma JAR, mod id e runtime metadata. CurseForge oficial confirma 0.7b, NeoForge 1.21.1, Client & Server, alimentos e compatibilidade opcional com Confectionery/Ecologics, além do fix de potions em jars. O repositório oficial `Antop90fr1/CreateFactory`, branch `1.21.1-dev`, confirma registries de foods/fluidos, classes de jar e integração JEI. O branch não é tag imutável da release; detalhes de código são usados como evidência da linha 1.21.1, não como prova binária byte-a-byte.
> 🔒 Boundary canônico: **Factory owns foods, jars, fluidos e suas recipes; Create owns a infraestrutura de processamento; integrações opcionais só existem quando o provider está realmente presente**.
