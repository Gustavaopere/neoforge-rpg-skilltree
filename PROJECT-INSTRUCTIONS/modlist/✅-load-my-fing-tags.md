# Load My F*ing Tags

## Propriedades do registro

- **Mod:** Load My F\*ing Tags
- **Arquivo JAR:** lmft-1.1.1+1.21.9-neoforge.jar
- **Versão 1.21.1:** 1.1.1+1.21.9
- **Categoria:** Biblioteca, QoL
- **Tipo de conteúdo:** Mod
- **Decisão:** Sem decisão
- **Estado da pesquisa:** Verificado
- **Estado no pack:** Integrado ao Github
- **Fonte:** https://www.curseforge.com/minecraft/mc-mods/lmft
- **Função:** Resiliência de tags: impede que entradas incorretas/ausentes invalidem uma tag inteira, permitindo carregar os membros válidos restantes.
- **Dependências:** Sem dependência obrigatória adicional publicada para a função base. A release NeoForge 1.1.1+1.21.9 foi construída como JAR multiversion; o autor declara suporte retroativo até Minecraft 1.20.2 no mesmo JAR.
- **Compatibilidade/Riscos:** Não corrige IDs ruins nem restaura objetos ausentes; pode mascarar defeitos de datapack ao permitir tag parcial. Riscos: client/server tag disagreement, recipes/AI/tools com membership parcial, reload divergente e perda silenciosa de semântica se logs forem ignorados.
- **Sobreposição:** Atua na infraestrutura de tags/datapacks, não substitui KubeJS/datapacks/providers. Apenas preserva membros válidos quando há referências inválidas; a semântica final da tag continua pertencendo ao datapack/mod que a define.
- **Observações:** O sufixo `+1.21.9` não significa incompatibilidade automática com 1.21.1: a release 1.1.1 declara suporte multiversion retroativo até 1.20.2. O mod tolera entradas inválidas; não deve ser usado como justificativa para deixar datapacks quebrados sem correção.
- **Procedência:** modlist.txt física reconferida em 13/09/2026 + CurseForge oficial LMFT 1.1.1+1.21.9, cujo changelog declara suporte multiversion NeoForge até 1.20.2 + projeto oficial; builds 1.21.11 posteriores não foram tratadas como atualização do runtime 1.21.1.
- **Atualização/Status:** PADRÃO ALEX'S MOBS REVALIDADO EM 13/09/2026 — LMFT 1.1.1+1.21.9/JAR físico reconfirmado. O artefato instalado continua válido para 1.21.1 pelo suporte multiversion retroativo até 1.20.2; builds posteriores para 1.21.11 mantêm a versão 1.1.1 e não são update necessário para este pack.
- **Histórico da decisão:** A auditoria anterior marcou o JAR para revisão por causa do rótulo 1.21.9. A documentação oficial da própria release esclarece que o JAR NeoForge 1.1.1 passou a suportar versões até 1.20.2, encerrando a dúvida de compatibilidade por versão.
- **Data da última decisão:** 2026-08-26

> **Autoridade física atual — 25/09/2026.** `modlist(1).txt` contém 587 entradas top-level incluindo o modloader; este item ocupa a ordem física #376: JAR `lmft-1.1.1+1.21.9-neoforge.jar`, mod id `lmft`, runtime `1.1.1+1.21.9`, SHA-1 `492d597ca216f9d099be112e77a9a5c7ed138770`.

<callout icon="🏷️" color="blue_bg">
	**ESCOPO CANÔNICO.** Runtime físico: `lmft-1.1.1+1.21.9-neoforge.jar`, mod id `lmft`, versão `1.1.1+1.21.9`. LMFT é uma camada de tolerância a falhas de **tags**: evita que referências incorretas/ausentes derrubem a tag inteira, preservando os membros válidos restantes.
</callout>
## 1. Identidade e compatibilidade multiversion
A publicação oficial corresponde exatamente ao JAR físico `lmft-1.1.1+1.21.9-neoforge.jar`. Embora o filename carregue `1.21.9`, o changelog oficial da própria release declara que a build NeoForge 1.1.1 foi preparada para suportar versões **até 1.20.2 para trás** em um único JAR. O upstream também introduziu groundwork de multiversion e `loom.allowMismatchedPlatformVersion=true`. Portanto o sufixo do arquivo não deve ser lido isoladamente como incompatibilidade com 1.21.1.
## 2. Papel no modpack
Tags são contratos data-driven usados por recipes, ferramentas, crops, combustíveis, AI, loot e integrações. Vanilla/modloader normalmente pode rejeitar uma tag quando ela referencia entries inexistentes, dependendo da forma/obrigatoriedade da entrada. LMFT altera essa falha para preservar as entradas que conseguem resolver.
## 3. O que LMFT realmente corrige
O projeto oficial resume a função como impedir que **incorrect tag entries** quebrem a tag inteira. Isso significa tolerância estrutural: se uma referência falhar, membros válidos podem continuar disponíveis. O mod não cria o item/bloco ausente, não corrige namespace digitado errado e não decide qual deveria ser o substituto semântico.
## 4. Limite crítico — não é saneador de datapack
Uma tag parcialmente carregada ainda pode estar funcionalmente errada. Exemplo: recipe aceita três materiais quando deveria aceitar quatro; mob ignora um alimento ausente; ferramenta perde uma categoria; integração não encontra um item opcional. LMFT mantém o jogo carregável, mas não transforma uma definição defeituosa em definição correta.
## 5. Logs e observabilidade
Entradas ausentes precisam continuar sendo tratadas como evidência de qualidade de dados. Em auditoria do pack, mensagens relacionadas a tags não devem ser descartadas apenas porque o jogo iniciou. O objetivo é identificar o provider/datapack responsável e corrigir a referência quando ela deveria existir.
## 6. Reload e datapack lifecycle
Tags são reconstruídas durante load/reload de resources/data. O resultado deve ser determinístico: a mesma coleção de mods/datapacks precisa produzir o mesmo membership após cold boot, `/reload` e restart. LMFT não deve acumular membros antigos nem transformar uma entry temporariamente ausente em state persistente fantasma.
## 7. Client / server boundary
Tags de gameplay usadas em recipes, loot, interactions e validation devem convergir com o state servidor. Mesmo quando resources relacionados existem no cliente, o cliente não deve inferir que um item pertence a uma tag se o servidor resolveu diferente. Mismatch de datapacks/mods continua sendo erro operacional.
## 8. Interação com outros mods do pack
Num pack com centenas de mods, LMFT reduz fan-out de uma referência opcional/removida, especialmente após updates. Porém isso também pode esconder compat patches obsoletos. KubeJS/datapacks próprios devem remover ou condicionar IDs ausentes em vez de depender permanentemente de LMFT para ignorá-los.
## 9. Migração e updates
Após remover ou atualizar um mod, compare tags críticas antes/depois. Se uma entry desapareceu intencionalmente, limpe o datapack/compat correspondente. Se desapareceu sem intenção, LMFT pode permitir boot mas o problema permanece. Atualização da própria LMFT deve ser regressada com datapack reload porque a superfície afetada é global.
## 10. Riscos técnicos
1. **Defeito mascarado:** pack inicia apesar de tag semanticamente incompleta.
2. **Recipe drift:** inputs/outputs mudam porque membership ficou parcial.
3. **AI/interaction drift:** entidades deixam de reconhecer alimento/bloco/item.
4. **Tool/harvest drift:** tags de ferramenta/mineração ficam incompletas.
5. **Client/server disagreement** em ambientes com arquivos diferentes.
6. **Reload divergence** se data packs mudarem entre sessões.
7. **False confidence:** interpretar ausência de crash como integridade de dados.
## 11. Matriz de testes
- [ ] Dedicated server inicia com LMFT 1.1.1 multiversion no runtime 1.21.1.
- [ ] Tag contendo uma entry inválida mantém membros válidos sem crash global.
- [ ] A entry inválida não é materializada/substituída por item incorreto.
- [ ] Logs ainda permitem localizar a definição problemática.
- [ ] `/reload` produz o mesmo membership que cold boot.
- [ ] Client e server concordam nas tags usadas por recipes/interactions críticas.
- [ ] Remover um mod opcional não deixa compat datapack silenciosamente incorreto.
- [ ] Corrigir a referência upstream restaura a tag completa sem workaround adicional.
Nenhum teste foi marcado como aprovado nesta auditoria documental.
## 12. Evidências e limites
Foram usados o JAR físico, a release oficial 1.1.1+1.21.9 e o repositório `Dragon-Seeker/LoadMyFingTags`. O suporte multiversion até 1.20.2 é declaração explícita da release NeoForge; não foi inferido apenas do filename. Não foram atribuídos mixins/classes específicos porque a finalidade operacional pode ser descrita e testada sem inventar internals não necessários.
