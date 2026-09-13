# Polymorph+

- **Banco de origem:** Auditoria Mestre da Modlist — NeoForge 1.21.1
- **Página Notion:** https://app.notion.com/p/3c369db9f0db81719f83e39af95f74d1
- **Estado no pack na exportação:** Instalado — Dossiê completo
- **Autoridade física usada:** `modlist 08.09.2026.txt` / `modlist.txt` — `polymorph_plus-neoforge-1.3.1+1.21.1.jar`, mod id `polymorph_plus`, runtime `1.3.1+1.21.1`; Ars Polymorphia 1.0.3 presente e Polymorph original ausente
- **Data da exportação:** 2026-09-11

## Divergência documental detectada na exportação

- A página Notion declara procedência por uma “modlist.txt física canônica atual de 10/09/2026”. A autoridade física mais recente realmente acessível nesta execução permanece o snapshot de **08/09/2026 com 595 top-levels**. Nesse snapshot, Polymorph+ 1.3.1 e Ars Polymorphia 1.0.3 estão presentes, enquanto o Polymorph original permanece ausente. O corpo-fonte abaixo é preservado sem reescrita silenciosa.

## Propriedades do banco

- **Mod:** Polymorph+
- **Arquivo JAR:** `polymorph_plus-neoforge-1.3.1+1.21.1.jar`
- **Versão 1.21.1:** 1.3.1+1.21.1
- **Estado no pack:** Instalado — Dossiê completo
- **Estado da pesquisa:** Verificado
- **Decisão:** Dependência
- **Categoria:** QoL, Compat
- **Função:** Expande o sistema de resolução de conflitos entre receitas, oferecendo seleção quando múltiplas receitas usam a mesma combinação de ingredientes.
- **Dependências:** NeoForge 1.21.1. Consumer operacional confirmado no pack: Ars Polymorphia 1.0.3 exige Polymorph; Polymorph+ declara compatibilidade com addons feitos para o Polymorph original. Ars Nouveau também está presente.
- **Sobreposição:** Substitui funcionalmente o Polymorph original nesta instalação e não deve coexistir com ele. Ars Polymorphia é integração consumidora, não duplicata.
- **Compatibilidade/Riscos:** Fork compatível com addons do Polymorph original e explicitamente incompatível com instalar o original junto. Riscos: addon/API drift, recipe reload, server/client selection mismatch e persistência do Crafter.
- **Observações:** Runtime 1.3.1+1.21.1, Release NeoForge publicada em 27/08/2026. Polymorph original e Polymorphic Energistics permanecem ausentes da modlist física atual.
- **Procedência:** modlist.txt física canônica atual de 10/09/2026 + CurseForge oficial Polymorph+ 1.3.1 + documentação oficial do fork + relações oficiais Ars Polymorphia 1.0.3.
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/polymorph-plus
- **Atualização/Status:** PADRÃO ALEX'S MOBS APLICADO EM 10/09/2026 — Polymorph+ 1.3.1 reconstruído e reclassificado como Dependência: fork boundary, Ars Polymorphia consumer, recipe selector/persistence, network/reload, riscos e testes.
- **Histórico da decisão:** 2026-09-10 — reclassificado de Sem decisão para Dependência: Ars Polymorphia 1.0.3 está fisicamente instalado e exige Polymorph; Polymorph+ é o único provider instalado e declara compatibilidade com addons do original.
- **Data da última decisão:** 2026-09-10

# Dossiê operacional — padrão Alex's Mobs

> **ESCOPO CANÔNICO.** Runtime físico: `polymorph_plus-neoforge-1.3.1+1.21.1.jar`, mod id `polymorph_plus`, versão `1.3.1+1.21.1`, NeoForge 1.21.1. Polymorph+ é fork/continuação do Polymorph original, com seleção de resultado quando receitas entram em conflito. O pack também contém **Ars Polymorphia 1.0.3**, que exige Polymorph; como Polymorph+ declara compatibilidade com addons feitos para o original e o original está ausente, esta instalação depende operacionalmente do fork e a decisão passa a **Dependência**.

## 1. Identidade e papel
- **Mod:** Polymorph+.
- **JAR físico:** `polymorph_plus-neoforge-1.3.1+1.21.1.jar`.
- **Mod id:** `polymorph_plus`.
- **Runtime:** `1.3.1+1.21.1`.
- **Loader/jogo:** NeoForge 1.21.1.
- **Ambiente:** Client & Server.
- **Licença:** LGPLv3.
- **Papel:** resolver recipe conflicts permitindo escolher o output desejado quando os mesmos ingredientes satisfazem múltiplas receitas.
- **Decisão:** Dependência.

## 2. Fork do Polymorph original
O projeto declara ser um fork que reproduz as funcionalidades do Polymorph original e acrescenta melhorias. Também declara duas boundaries explícitas:
- compatível com addons feitos para o Polymorph original;
- **incompatível com instalar Polymorph original simultaneamente**.

A modlist atual contém apenas Polymorph+ como implementação top-level desse sistema, portanto não há duplicata original instalada.

## 3. Consumer causal: Ars Polymorphia
O pack físico contém `ars_polymorphia-1.0.3.jar`.

Ars Polymorphia publica como required content:
- Ars Nouveau;
- Polymorph.

Sua função é levar o seletor de recipes ao **Storage Lectern** do Ars Nouveau. Polymorph+ declara compatibilidade com addons feitos para Polymorph original.

Assim, a instalação atual usa Polymorph+ como provider compatível para esse addon; remover o fork quebraria/degradaria a integração presente.

## 4. Recipe conflict selection
Quando um conjunto de ingredientes corresponde a mais de uma receita, o sistema oferece uma UI de seleção para escolher o resultado.

A recipe continua pertencendo ao provider original. Polymorph+ não reescreve a lógica funcional do output; resolve a ambiguidade de escolha.

Testar crafting vanilla, crafting table modded, recipes com tags e recipes dinâmicas.

## 5. Persistência da seleção
O fork publica persistência de seleção para o Crafter block em 1.21+, permitindo redstone automation manter o resultado escolhido.

Isso é uma superfície server-authoritative importante:
- seleção deve persistir com o bloco;
- save/restart não pode resetar indevidamente;
- mudança de ingredientes deve invalidar seleção quando necessário;
- redstone repetido não deve alternar output sozinho.

## 6. UI ampliada do fork
A linha do fork adiciona:
- seletor scrollável quando há mais de sete receitas;
- navegação por setas/mouse wheel;
- painel pinável durante a sessão;
- tutorial de primeira execução.

Essas features são presentation/QoL; a receita escolhida ainda precisa convergir ao servidor.

## 7. Linha 1.3.1
A build física 1.3.1 é Release NeoForge 1.21.1 publicada em 27/08/2026.

A família 1.3.x adiciona melhorias como favoritos/atalhos e refinamentos de layout/preferência. Como a página exata 1.21.1 não publicou aqui um changelog detalhado por alvo além da release family, esta ficha evita atribuir comportamento específico não verificado ao binário quando não necessário.

## 8. Ars Polymorphia / Storage Lectern
Ars Polymorphia adiciona a seleção de conflitos ao terminal do Storage Lectern. A documentação publica que:
- um botão aparece quando há múltiplos resultados;
- a lista permite selecionar o resultado;
- a seleção é lembrada enquanto os ingredientes não mudam;
- 1.0.3 corrige recipes com resultado vazio.

O teste deve garantir que Polymorph+ realmente satisfaz essa compatibilidade publicada na instalação atual.

## 9. Network e server authority
A UI de seleção existe no cliente, mas o output craftado deve ser validado no servidor. Riscos:
- cliente escolher recipe que servidor não reconhece;
- seleção stale após recipe reload;
- container fechado antes de sync;
- spam de seleção sob latência;
- discrepância entre recipe list de cliente/servidor.

## 10. `/reload` e recipe lifecycle
Datapacks/KubeJS/mod updates podem alterar recipes. Após `/reload`:
- selector precisa refletir conjunto atual;
- favorites/prefs inválidos devem falhar de modo controlado;
- seleção persistida para recipe removida não pode gerar output fantasma;
- Ars Storage Lectern deve reconstruir options corretamente.

## 11. Compatibilidade com recipe mods
Em modpack grande, conflitos podem envolver Create, Farmer's Delight, Ars, crafting vanilla e inúmeros addons.

Polymorph+ deve resolver ambiguidade sem assumir ownership dos recipes. Se um recipe está incorreto, investigar o provider; se dois recipes válidos conflitam e selector falha, Polymorph+/integration é a superfície correta.

## 12. Riscos
1. **Fork compatibility:** addon Polymorph precisa funcionar realmente contra Polymorph+.
2. **Original conflict:** instalar Polymorph original junto é explicitamente incompatível.
3. **Recipe reload:** seleção/favorites podem apontar para recipe removida.
4. **Server/client mismatch:** resultado selecionado precisa ser autorizado pelo servidor.
5. **Automation persistence:** Crafter não pode alternar resultado após restart.
6. **Large selector:** muitas receitas podem estressar UI/layout.
7. **Addon drift:** Ars Polymorphia pode esperar API behavior de outra versão.
8. **Recipe provider attribution:** Polymorph+ não deve ser culpado por recipe mal definida.

## 13. Matriz de testes
- [ ] Dedicated server e cliente iniciam com Polymorph+ 1.3.1 + Ars Polymorphia 1.0.3.
- [ ] Duas recipes com mesmos ingredientes exibem selector e craftam a escolhida.
- [ ] Mais de sete conflitos usam scroll sem cortar opções.
- [ ] Crafter persiste seleção após save/restart e redstone automation.
- [ ] Mudança dos ingredientes invalida seleção antiga corretamente.
- [ ] Storage Lectern do Ars mostra selector via Ars Polymorphia.
- [ ] Recipe com output vazio é ignorada pela integração 1.0.3.
- [ ] `/reload` atualiza options sem stale recipes.
- [ ] Latência não permite craft de recipe não autorizada pelo servidor.
- [ ] Polymorph original continua ausente; nenhuma dupla implementação é carregada.

Nenhum teste foi marcado como aprovado nesta auditoria documental.

## 14. Evidências e limites
- Modlist física: Polymorph+ 1.3.1, mixins de core/compatibility e Ars Polymorphia 1.0.3 presente; Polymorph original ausente.
- CurseForge oficial Polymorph+: Release NeoForge 1.21.1 1.3.1 de 27/08/2026; fork declara compatibilidade com addons do original e conflito com o original instalado junto.
- Ars Polymorphia 1.0.3: required content = Ars Nouveau + Polymorph; função = recipe conflict selection no Storage Lectern; 1.0.3 ignora recipes com output vazio.
- **Limite:** a compatibilidade publicada do fork precisa ser confirmada por smoke real da dupla Ars Polymorphia↔Polymorph+; a classificação Dependência registra a relação operacional pretendida, não inventa equivalência de mod id interna.
