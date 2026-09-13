# CraftedCore

> Fonte canônica desta importação: Notion — `Auditoria Mestre da Modlist — NeoForge 1.21.1`  
> Página-fonte: https://app.notion.com/p/3c969db9f0db815b8de6c730d703bde3  
> Estado no momento da exportação: `Instalado — Dossiê completo`  
> Autoridade física no momento da exportação: `modlist.txt`, 595 mods top-level  
> Exportado em: 2026-09-09

## Propriedades do registro

- **Mod:** CraftedCore
- **Arquivo JAR:** `craftedcore-5.8.2.jar`
- **Versão 1.21.1:** `5.8.2`
- **Categoria:** Biblioteca
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack na origem:** Instalado — Dossiê completo
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/crafted-core
- **Função:** Biblioteca/API compartilhada da ToCraft usada por mods consumidores; centraliza código e dados comuns sem adicionar um sistema jogável autônomo.
- **Dependências:** Library Client & Server. A necessidade deve ser determinada pelos consumidores instalados que declaram CraftedCore; nenhuma dependência externa adicional foi inferida sem metadata versionada do consumer.
- **Compatibilidade/Riscos:** Riscos principais: remoção com consumer ativo, version/API drift e dependência de dados/recursos compartilhados. A 5.8.2 remove data files destinados a versões anteriores a Minecraft 1.20.1; não atribuir mudança de gameplay à release.
- **Sobreposição:** Biblioteca específica dos consumers ToCraft. Similaridade com outras core libraries não implica substituição binária; cada consumer depende de contracts próprios.
- **Observações:** runtime 5.8.2; arquivo oficial `craftedcore-5.8.2.jar`. Changelog 5.8.2: remoção de data files para versões anteriores a 1.20.1. Projeto classificado como API/library, Client & Server.
- **Procedência:** modlist.txt física atual de 08/09/2026 (595 top-levels) + runtime 5.8.2 + CurseForge oficial CraftedCore 1.21-NeoForge 5.8.2 + documentação oficial ToCraft.
- **Histórico da decisão:** Sem decisão formal. Em 08/09/2026, CraftedCore 5.8.2 foi reconfirmado no pack físico e normalizado como biblioteca consumer-driven; a presença não foi convertida em decisão curatorial.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 09/09/2026 — CraftedCore 5.8.2, consumer-driven library contract, shared data/resources, side/lifecycle e delta 5.8.2 confirmados no QC global #113. Runtime QA não executado.
- **Data da última decisão:** não definida.

## Dossiê operacional — padrão Alex's Mobs

> 📚 Versão física confirmada: `craftedcore-5.8.2.jar`, runtime `5.8.2`, NeoForge 1.21.1. CraftedCore é uma **library/API da ToCraft**; não adiciona um loop de gameplay autônomo.

## 1. Papel e authority
CraftedCore concentra código/infraestrutura comum para mods consumidores. O consumer continua authority de seus registries, entidades, gameplay, configs e dados finais.

Não catalogar uma utility da library como feature do jogador sem apontar o consumer que a utiliza.

## 2. Dependency graph
A necessidade é consumer-driven. Remover CraftedCore enquanto um mod instalado a declara pode impedir carregamento ou produzir linkage errors.

Antes de remoção, mapear consumidores físicos atuais; não inferir dependência apenas pela autoria ou pelo nome do projeto.

## 3. Release 5.8.2
O changelog oficial da build NeoForge 1.21/1.21.1 registra uma alteração estreita: **remoção de data files para versões anteriores a Minecraft 1.20.1**.

Essa limpeza não autoriza inferir mudanças de API ou gameplay não listadas.

## 4. Dados e recursos compartilhados
Como core library, CraftedCore pode fornecer data/assets/helpers usados por consumers. Ownership do conteúdo carregado continua no consumer quando o recurso representa sua feature.

Missing resource depois de atualização deve ser diagnosticado com versão da library + consumer + caminho/registry afetado.

## 5. Client/server
A distribuição é Client & Server. Common code pode ser usado em ambos; renderer/screens de consumers permanecem client-only e não devem ser puxados para dedicated server por classloading indevido.

## 6. Lifecycle
Validar mod construction, bootstrap dos consumers, world join, resource/datapack reload quando aplicável, disconnect/reconnect e server restart.

Shared callbacks/caches não devem ser registrados duas vezes nem sobreviver a um lifecycle que exige reconstrução.

## 7. Version drift
Sintomas possíveis:
- `NoClassDefFoundError`/`NoSuchMethodError`;
- consumer que não conclui bootstrap;
- data/resource compartilhado ausente;
- comportamento parcial após update.

Atualizar library isoladamente exige smoke-test do conjunto real de consumers.

## 8. Fail-closed de API
A página pública não fornece inventário estático de classes/métodos da 5.8.2. Portanto esta ficha não inventa símbolos concretos.

Integrações próprias devem inspecionar source/JAR/tag correspondente antes de compilar contra API específica.

## 9. Riscos
1. Remover com consumer ativo.
2. Atualizar fora da faixa esperada pelo consumer.
3. Confundir core library com provider de gameplay.
4. Resource/data antigo ainda referenciado por consumer legado.
5. Client-only class em dedicated server.
6. Cache/callback duplicado após reload.

## 10. Matriz de testes
1. Dedicated server boot com consumers atuais.
2. Client boot/join sem linkage errors.
3. Smoke-test de cada consumer confirmado.
4. Resource/datapack reload quando o consumer usa dados compartilhados.
5. Restart/reconnect sem duplicate registration.
6. Atualização futura: library e consumers testados em conjunto.

## 11. Evidência
- modlist física atual: CraftedCore 5.8.2;
- CurseForge oficial: API/library Client & Server para NeoForge 1.21/1.21.1;
- changelog 5.8.2: remove data files destinados a versões anteriores a 1.20.1;
- nenhuma API concreta não documentada foi promovida por inferência.

> 🔧 Boundary canônico: CraftedCore fornece **infraestrutura compartilhada**; gameplay final permanece sob authority dos mods consumidores.
